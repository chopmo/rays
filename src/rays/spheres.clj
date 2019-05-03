(ns rays.spheres
  (:require [rays.tuples :as t]
            [rays.rays :as r]))

(defn sphere []
  (name (gensym)))

(defn intersect [sphere ray]
  (let [sphere-to-ray (t/subtract (r/origin ray) (t/->point 0 0 0))
        ray-dir       (r/direction ray)
        a             (t/dot ray-dir ray-dir)
        b             (* 2 (t/dot ray-dir sphere-to-ray))
        c             (- (t/dot sphere-to-ray sphere-to-ray) 1)
        disc          (- (* b b)
                         (* 4 a c))]
    (if (< disc 0)
      []
      [(/ (- (- b) (Math/sqrt disc)) (* 2 a))
       (/ (+ (- b) (Math/sqrt disc)) (* 2 a))])))
