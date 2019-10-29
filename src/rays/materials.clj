(ns rays.materials
  (:require [rays.colors :as c]
            ))

(defn material []
  {:color c/white
   :ambient 0.1
   :diffuse 0.9
   :specular 0.9
   :shininess 200.0})

(defn color [m]
  (:color m))

(defn ambient [m]
  (:ambient m))

(defn diffuse [m]
  (:diffuse m))

(defn specular [m]
  (:specular m))

(defn shininess [m]
  (:shininess m))
