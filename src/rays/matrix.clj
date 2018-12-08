(ns rays.matrix
  (:require [rays.common :as cm]
            [rays.tuples :as t]))

(defn ->mat2 [& xs]
  (vec (map vec (partition 2 xs))))

(defn ->mat3 [& xs]
  (vec (map vec (partition 3 xs))))

(defn ->mat4 [& xs]
  (vec (map vec (partition 4 xs))))

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
           (+ (* (at a r 0) (at b 0 c))
              (* (at a r 1) (at b 1 c))
              (* (at a r 2) (at b 2 c))
              (* (at a r 3) (at b 3 c))))))

(defn mul-tuple [a t]
  (apply t/->tuple
         (for [i (range 4)]
           (+ (* (at a i 0) (:x t))
              (* (at a i 1) (:y t))
              (* (at a i 2) (:z t))
              (* (at a i 3) (:w t))))))
