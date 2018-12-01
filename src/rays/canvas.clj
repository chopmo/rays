(ns rays.canvas
  (:require [rays.colors :as col]))

(defn ->canvas [w h]
  (into []
        (repeat h
                (into [] (repeat w col/black)))))

(defn width [c]
  (count (first c)))

(defn height [c]
  (count c))
