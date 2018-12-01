(ns rays.ppm
  (:require [clojure.string :as str]
            [rays.canvas :as c]))

(defn canvas-to-ppm [c]
  (str/join "\n"
            ["P3"
             (str (c/width c) " " (c/height c))
             "255"]) )
