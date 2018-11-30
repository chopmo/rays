(ns rays.tuples)

(defn ->tuple [x y z w]
  {:x x :y y :z z :w w})

(defn ->point [x y z]
  (->tuple x y z 1))

(defn ->vector [x y z]
  (->tuple x y z 0))

(defn point? [t]
  (= 1.0 (:w t)))

(defn vector? [t]
  (zero? (:w t)))
