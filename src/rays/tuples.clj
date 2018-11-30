(ns rays.tuples)

(defn point? [t]
  (= 1.0 (:w t)))

(defn vector? [t]
  (zero? (:w t)))
