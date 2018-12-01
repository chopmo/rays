(ns rays.canvas-test
  (:require [rays.canvas :as sut]
            [clojure.test :refer [deftest testing is]]
            [rays.colors :as col]))

(deftest test-canvas
  (testing "creating a canvas"
    (let [c (sut/->canvas 10 20)]
      (is (= 10 (sut/width c)))
      (is (= 20 (sut/height c)))
      (is (col/equal? col/black (ffirst c))))))
