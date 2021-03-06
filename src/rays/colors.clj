(ns rays.colors
  (:require [rays.common :as c]))

(defn ->color [r g b]
  {:red r
   :green g
   :blue b})

(def black (->color 0 0 0))
(def white (->color 1 1 1))

(defn equal? [c1 c2]
  (and (c/eq-floats? (:red c1) (:red c2))
       (c/eq-floats? (:green c1) (:green c2))
       (c/eq-floats? (:blue c1) (:blue c2))))

(defn add [c1 c2]
  (->color (+ (:red c1) (:red c2))
           (+ (:green c1) (:green c2))
           (+ (:blue c1) (:blue c2))))

(defn subtract [c1 c2]
  (->color (- (:red c1) (:red c2))
           (- (:green c1) (:green c2))
           (- (:blue c1) (:blue c2))))

(defn multiply [c1 c2]
  (->color (* (:red c1) (:red c2))
           (* (:green c1) (:green c2))
           (* (:blue c1) (:blue c2))))

(defn red [c] (:red c))
(defn green [c] (:green c))
(defn blue [c] (:blue c))
