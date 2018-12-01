(ns rays.canvas-test
  (:require [rays.canvas :as sut]
            [clojure.test :refer [deftest testing is]]
            [rays.colors :as col]))

(deftest test-canvas
  (testing "creating a canvas"
    (let [c (sut/->canvas 10 20)]
      (is (= 10 (sut/width c)))
      (is (= 20 (sut/height c)))
      (is (col/equal? col/black (ffirst c)))))

  (testing "writing pixesls to a canvas"
    (let [c (sut/->canvas 10 20)
          red (col/->color 1 0 0)
          c (sut/write-pixel c 2 3 red)]
      (is (col/equal? (col/->color 1 0 0)
                      (sut/pixel-at c 2 3))))))
