(ns rays.common)

(def epsilon 0.00001)

(defn eq-floats? [a b]
  (< (Math/abs (- a b)) epsilon))
