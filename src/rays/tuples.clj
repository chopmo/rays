(ns rays.tuples
  (:require [rays.common :as c])
  (:import [java.lang Math]))

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
  (c/eq-floats? 1.0 (:w t)))

(defn vect? [t]
  (c/eq-floats? 0.0 (:w t)))

(defn equal? [t1 t2]
  (and (c/eq-floats? (:x t1) (:x t2))
       (c/eq-floats? (:y t1) (:y t2))
       (c/eq-floats? (:z t1) (:z t2))
       (c/eq-floats? (:w t1) (:w t2))))

(defn magnitude [t]
  (Math/sqrt
   (+ (* (:x t) (:x t))
      (* (:y t) (:y t))
      (* (:z t) (:z t))
      (* (:w t) (:w t)))))

(defn normalize [t]
  (divide t (magnitude t)))

(defn dot [t1 t2]
  (+ (* (:x t1) (:x t2))
     (* (:y t1) (:y t2))
     (* (:z t1) (:z t2))
     (* (:w t1) (:w t2))))

(defn cross [v1 v2]
  (->vect (- (* (:y v1) (:z v2)) (* (:z v1) (:y v2)))
          (- (* (:z v1) (:x v2)) (* (:x v1) (:z v2)))
          (- (* (:x v1) (:y v2)) (* (:y v1) (:x v2)))))
