(ns rays.clock
  (:require [rays.canvas :as c]
            [rays.colors :as col]
            [rays.matrix :as m]
            [rays.ppm :as ppm]
            [rays.tuples :as t]))

(comment
  (let [width 400
        height 400
        size 150
        canvas (c/->canvas width height)
        add-point (fn [canvas pos]
                    (let [rotate (m/rotation-z
                                  (* pos (/ (* 2 (Math/PI)) 12)))
                          scale (m/scaling size size 1)
                          move (m/translation (/ width 2)
                                              (/ height 2)
                                              0)
                          transform (-> move
                                        (m/mul4 scale)
                                        (m/mul4 rotate))
                          p (m/mul-tuple transform
                                         (t/->point 1 0 0))]
                      (c/write-pixel canvas
                                     (-> p t/x int)
                                     (-> p t/y int)
                                     (col/->color 1 1 1))))]
    (->>
     (reduce add-point canvas (range 12))
     ppm/canvas-to-ppm
     (spit "clock.ppm")))
  )
