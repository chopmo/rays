(ns rays.canvas
  (:require [rays.colors :as col]))

(defn ->canvas [w h & [color]]
  (into []
        (repeat h
                (into [] (repeat w (or color
                                       col/black))))))

(defn width [c]
  (count (first c)))

(defn height [c]
  (count c))

(defn write-pixel [c x y col]
  (if (and (<= 0 x (dec (width c)))
           (<= 0 y (dec (height c))))
    (assoc-in c [y x] col)
    c))

(defn pixel-at [c x y]
  (get-in c [y x]))
