(ns rays.matrix
  (:require [rays.common :as cm]))

(defn ->mat2 [& xs]
  (vec (map vec (partition 2 xs))))

(defn ->mat3 [& xs]
  (vec (map vec (partition 3 xs))))

(defn ->mat4 [& xs]
  (vec (map vec (partition 4 xs))))


(defn at [m r c]
  (get-in m [r c]))

(defn eq [m1 m2]
  (every? (fn [[a b]] (cm/eq-floats? a b))
          (map vector (flatten m1) (flatten m2))))
