(ns rays.matrix
  (:require [rays.common :as cm]
            [rays.tuples :as t]))

(defn ->mat2 [& xs]
  (vec (map vec (partition 2 xs))))

(defn ->mat3 [& xs]
  (vec (map vec (partition 3 xs))))

(defn ->mat4 [& xs]
  (vec (map vec (partition 4 xs))))

(def identity
  (->mat4 1 0 0 0
          0 1 0 0
          0 0 1 0
          0 0 0 1))

(defn at [m r c]
  (get-in m [r c]))

(defn eq [m1 m2]
  (every? (fn [[r1 r2]]
            (every? (partial apply cm/eq-floats?) (map vector r1 r2)))
          (map vector m1 m2)))

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
