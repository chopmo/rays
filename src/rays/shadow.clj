(ns rays.shadow
  (:require [rays.canvas :as c]
            [rays.colors :as col]
            [rays.ppm :as ppm]
            [rays.rays :as r]
            [rays.spheres :as s]
            [rays.tuples :as t]))

(let [canvas-size 100
      canvas      (c/->canvas canvas-size
                              canvas-size
                              (col/->color 0 0 0))
      ray-origin  (t/->point 0 0 -5)
      wall-z      10
      wall-size   7.0

      s          (s/sphere)
      step       2

      pixel->coord (fn [x]
                     (* (- x (/ canvas-size 2))
                        (/ wall-size canvas-size)))

      add-point (fn [canvas [x y]]
                  (let [wall-vector (t/->vect (pixel->coord x)
                                              (pixel->coord y)
                                              wall-z)
                        r           (r/ray ray-origin wall-vector)]
                    (if (seq (s/intersect s r))
                      (c/write-pixel canvas x y (col/->color 1 0 0))
                      canvas)))]
  (->>
   (reduce add-point
           canvas
           (for [x (range 0 canvas-size step)
                 y (range 0 canvas-size step)]
             [x y]))
   ppm/canvas-to-ppm
   (spit "shadow.ppm")))
