(ns rays.lights)

(defn point-light [position intensity]
  {:position position
   :intensity intensity})

(defn position [light]
  (:position light))

(defn intensity [light]
  (:intensity light))
