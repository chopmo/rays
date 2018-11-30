(ns rays.tuples
  (:import [java.lang.Math]))

(def epsilon 0.00001)

(defn- eq-floats? [a b]
  (< (Math/abs (- a b)) epsilon))

(defn ->tuple [x y z w]
  {:x x :y y :z z :w w})

(defn ->point [x y z]
  (->tuple x y z 1))

(defn ->vect [x y z]
  (->tuple x y z 0))

(defn add [t1 t2]
  {:x (+ (:x t1) (:x t2))
   :y (+ (:y t1) (:y t2))
   :z (+ (:z t1) (:z t2))
   :w (+ (:w t1) (:w t2))})

(defn subtract [t1 t2]
  {:x (- (:x t1) (:x t2))
   :y (- (:y t1) (:y t2))
   :z (- (:z t1) (:z t2))
   :w (- (:w t1) (:w t2))})

(defn negate [t]
  (subtract (->tuple 0 0 0 0) t))

(defn multiply [t s]
  {:x (* (:x t) s)
   :y (* (:y t) s)
   :z (* (:z t) s)
   :w (* (:w t) s)})

(defn divide [t s]
  (multiply t (float (/ 1 s))))

(defn point? [t]
  (eq-floats? 1.0 (:w t)))

(defn vect? [t]
  (eq-floats? 0.0 (:w t)))

(defn equal? [t1 t2]
  (and (eq-floats? (:x t1) (:x t2))
       (eq-floats? (:y t1) (:y t2))
       (eq-floats? (:z t1) (:z t2))
       (eq-floats? (:w t1) (:w t2))))
