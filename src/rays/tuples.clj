(ns rays.tuples
  (:import [java.lang.Math]))

(def epsilon 0.00001)

(defn- eq-floats? [a b]
  (< (Math/abs (- a b)) epsilon))

(defn ->tuple [x y z w]
  {:x x :y y :z z :w w})

(defn ->point [x y z]
  (->tuple x y z 1))

(defn ->vector [x y z]
  (->tuple x y z 0))

(defn point? [t]
  (eq-floats? 1.0 (:w t)))

(defn vector? [t]
  (eq-floats? 0.0 (:w t)))
