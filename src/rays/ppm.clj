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

(defn wrap [len s]
  (letfn [(helper [s idx ss]
            (cond
              ;; Couldn't split the string, so just append it
              (zero? idx)
              (let [[w & ws] (str/split s #" ")]
                (helper (str/join " " ws)
                        len
                        (conj ss w)))

              (empty? s)
              ss

              (<= (count s) len)
              (conj ss s)

              (= \space (get s idx))
              (helper (subs s (inc idx))
                      len
                      (conj ss (subs s 0 idx)))

              :default
              (helper s
                      (dec idx)
                      ss)))]
    (helper s len [])))

(defn canvas-to-ppm [c]
  (let [header     ["P3"
                    (str (c/width c) " " (c/height c))
                    "255"]
        pixel-rows (->> c
                        (map row)
                        (mapcat (partial wrap 70)))]
    (str/join "\n"
              (concat header pixel-rows))) )
