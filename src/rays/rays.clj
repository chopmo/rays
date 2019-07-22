(ns rays.rays
  (:require [rays.tuples :as t]
            [rays.matrix :as m]))


(defn ray [origin direction]
  {:origin origin
   :direction direction})

(defn origin [ray]
  (:origin ray))

(defn direction [ray]
  (:direction ray))

(defn position [{:keys [origin direction] :as _ray} t]
  (t/add origin (t/multiply direction t)))

(defn transform [r matrix]
  (ray (m/mul-tuple matrix (origin r))
       (m/mul-tuple matrix (direction r))))
