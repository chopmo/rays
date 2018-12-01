(ns rays.ppm-test
  (:require  [clojure.test :refer [deftest testing is]]
             [rays.ppm :as sut]
             [rays.canvas :refer [->canvas]]
             [clojure.string :as str]))


(deftest test-ppm
  (testing "constructing the PPM header"
    (let [c (->canvas 5 3)
          ppm (sut/canvas-to-ppm c)]
      (is (= ["P3"
              "5 3"
              "255"]
             (take 3 (str/split-lines ppm)))))))
