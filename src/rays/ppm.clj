(ns rays.ppm
  (:require [clojure.string :as str]
            [rays.canvas :as c]
            [rays.colors :as col])
  (:import [java.lang Math]))

(defn- scale [x]
  (* 255 x))

(defn- round [x]
  (if (float? x)
    (Math/round x)
    x))

(defn- clamp [x]
  (min 255 (max 0 x)))

(defn- row [r]
  (->> r
       (map (fn [col] [(col/red col)
                      (col/green col)
                      (col/blue col)]))
       (apply concat)
       (map (comp clamp round scale))
       (str/join " ")))

(defn canvas-to-ppm [c]
  (let [header ["P3"
                (str (c/width c) " " (c/height c))
                "255"]
        pixel-rows (map row c)]
    (str/join "\n"
              (concat header pixel-rows))) )
