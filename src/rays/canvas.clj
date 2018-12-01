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

(defn write-pixel [c x y col]
  (assoc-in c [y x] col))

(defn pixel-at [c x y]
  (get-in c [y x]))
