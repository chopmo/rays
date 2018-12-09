(ns rays.matrix
  (:require [rays.common :as cm]
            [rays.tuples :as t]))

(defn ->mat2 [& xs]
  (vec (map vec (partition 2 xs))))

(defn ->mat3 [& xs]
  (vec (map vec (partition 3 xs))))

(defn ->mat4 [& xs]
  (vec (map vec (partition 4 xs))))

(def ident
  (->mat4 1 0 0 0
          0 1 0 0
          0 0 1 0
          0 0 0 1))

(defn at [m r c]
  (get-in m [r c]))

(defn eq [m1 m2]
  (and (= (count m1) (count m2))
       (= (count (first m1)) (count (first m2)))
       (every? (fn [[r1 r2]]
                 (every? (partial apply cm/eq-floats?) (map vector r1 r2)))
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
           (+ (* (at a r 0) (:x t))
              (* (at a r 1) (:y t))
              (* (at a r 2) (:z t))
              (* (at a r 3) (:w t))))))

(defn transpose [m]
  (apply map vector m))

(defn determinant [m]
  (let [size (count m)]
    (if (> size 2)
      (let [r         (first m)
            cofactors (map (fn [i] (cofactor m 0 i)) (range size))]
        (reduce + (map * r cofactors)))
      (- (* (at m 0 0) (at m 1 1))
         (* (at m 0 1) (at m 1 0))))))


(defn submatrix [m r c]
  (->> (vec (concat (subvec m 0 r) (subvec m (inc r))))
       (map (fn [r] (vec (concat (subvec r 0 c) (subvec r (inc c))))))
       vec))

(defn minor [m r c]
  (determinant (submatrix m r c)))

(defn cofactor [m r c]
  (let [minor (minor m r c)]
    (if (odd? (+ r c))
      (- minor)
      minor)))
