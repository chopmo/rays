(ns rays.ppm-test
  (:require  [clojure.test :refer [deftest testing is]]
             [rays.ppm :as sut]
             [rays.canvas :as c]
             [rays.colors :refer [->color]]
             [clojure.string :as str]
             ))


(deftest test-ppm
  (testing "constructing the PPM header"
    (let [c (c/->canvas 5 3)
          ppm (sut/canvas-to-ppm c)]
      (is (= ["P3"
              "5 3"
              "255"]
             (take 3 (str/split-lines ppm))))))

  (testing "constructing the ppm pixel data"
    (let [c1 (->color 1.5 0 0)
          c2 (->color 0 0.5 0)
          c3 (->color -0.5 0 1)
          c (-> (c/->canvas 5 3)
                (c/write-pixel 0 0 c1)
                (c/write-pixel 2 1 c2)
                (c/write-pixel 4 2 c3))
          ppm (sut/canvas-to-ppm c)
          pixel-lines (->> ppm
                           str/split-lines
                           (drop 3)
                           (take 3))]
      (is (= ["255 0 0 0 0 0 0 0 0 0 0 0 0 0 0"
            "0 0 0 0 0 0 0 128 0 0 0 0 0 0 0"
            "0 0 0 0 0 0 0 0 0 0 0 0 0 0 255"]
             pixel-lines)))))