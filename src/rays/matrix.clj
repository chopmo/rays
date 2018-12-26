(ns rays.matrix
  (:require [rays.common :as cm]
            [rays.tuples :as t]))

(defn ->mat2 [& xs]
  (vec xs))

(defn ->mat3 [& xs]
  (vec xs))

(defn ->mat4 [& xs]
  (vec xs))

(def ident
  (->mat4 1 0 0 0
          0 1 0 0
          0 0 1 0
          0 0 0 1))

(defn- dim [m]
  (case (count m)
    16 4
    9  3
    4  2))

(defn- idx [m r c]
  (+ (* (dim m) r) c))

(defn at [m r c]
  (nth m (idx m r c)))

(defn set-val [m r c val]
  (assoc m (idx m r c) val))

(defn eq [m1 m2]
  (and (= (count m1) (count m2))
       (every? (fn [[x1 x2]]
                 (cm/eq-floats? x1 x2))
               (map vector m1 m2))))

(defn mul4 [a b]
  (apply ->mat4
         (for [r (range 4)
               c (range 4)]
           (apply +
                  (for [i (range 4)]
                    (* (at a r i) (at b i c)))))))

(defn mul-tuple [a t]
  (apply t/->tuple
         (for [r (range 4)]
           (+ (* (at a r 0) (t/x t))
              (* (at a r 1) (t/y t))
              (* (at a r 2) (t/z t))
              (* (at a r 3) (t/w t))))))

(defn transpose [m]
  (let [rows (partition (dim m) m)]
    (vec (flatten (apply map vector rows)))))

(declare minor)

(defn cofactor [m r c]
  (let [minor (minor m r c)]
    (if (odd? (+ r c))
      (- minor)
      minor)))

(defn determinant [m]
  (let [size (dim m)]
    (if (> size 2)
      (let [r0        (take size m)
            cofactors (map (fn [i] (cofactor m 0 i)) (range size))]
        (reduce + (map * r0 cofactors)))
      (- (* (at m 0 0) (at m 1 1))
         (* (at m 0 1) (at m 1 0))))))

(defn submatrix [m r c]
  (let [d (dim m)
        rows (filter #(not (= r %)) (range d))
        cols (filter #(not (= c %)) (range d))]
    (vec
     (for [row rows
           col cols]
       (at m row col)))))

(defn minor [m r c]
  (determinant (submatrix m r c)))

(defn invertible? [m]
  (not (cm/eq-floats? 0 (determinant m))))

(defn inverse [m]
  (let [matrix-of-cofactors
        (apply ->mat4
               (for [r (range 4)
                     c (range 4)]
                 (cofactor m r c)))

        transposed (transpose matrix-of-cofactors)
        d          (determinant m)]
    (apply ->mat4
           (for [r (range 4)
                 c (range 4)]
             (/ (at transposed r c) d)))))

(defn translation [x y z]
  (-> ident
      (set-val 0 3 x)
      (set-val 1 3 y)
      (set-val 2 3 z)))

(defn scaling [x y z]
  (-> ident
      (set-val 0 0 x)
      (set-val 1 1 y)
      (set-val 2 2 z)))
