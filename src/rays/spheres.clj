(ns rays.spheres
  (:require [rays.tuples :as t]
            [rays.rays :as r]
            [rays.matrix :as m]))

(defn sphere []
  {:transform m/ident})

(defn get-transform [s]
  (:transform s))

(defn set-transform [s t]
  (assoc s :transform t))

(defn intersect [sphere ray]
  (let [;; Transform the ray by the inverse of the sphere's
        ;; transformation
        ray           (r/transform ray (m/inverse (get-transform sphere)))
        sphere-to-ray (t/subtract (r/origin ray) (t/->point 0 0 0))
        ray-dir       (r/direction ray)
        a             (t/dot ray-dir ray-dir)
        b             (* 2 (t/dot ray-dir sphere-to-ray))
        c             (- (t/dot sphere-to-ray sphere-to-ray) 1)
        disc          (- (* b b)
                         (* 4 a c))]
    (if (< disc 0)
      []
      [{:t (/ (- (- b) (Math/sqrt disc)) (* 2 a))
        :object sphere}
       {:t (/ (+ (- b) (Math/sqrt disc)) (* 2 a))
        :object sphere}])))
