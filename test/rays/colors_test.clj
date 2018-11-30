(ns rays.colors-test
  (:require [rays.colors :as sut]
            [clojure.test :refer [testing deftest is]]
            [rays.common :as c]))

(deftest colors-test
  (testing "constructing a color"
    (let [c (sut/->color 0.1 0.2 0.3)]
      (is (c/eq-floats? 0.1 (:red c)))
      (is (c/eq-floats? 0.2 (:green c)))
      (is (c/eq-floats? 0.3 (:blue c)))))

  (testing "adding colors"
    (let [c1 (sut/->color 0.9 0.6 0.75)
          c2 (sut/->color 0.7 0.1 0.25)]
      (is (sut/equal? (sut/->color 1.6 0.7 1.0)
                      (sut/add c1 c2)))))

  (testing "subtracting colors"
    (let [c1 (sut/->color 0.9 0.6 0.75)
          c2 (sut/->color 0.7 0.1 0.25)]
      (is (sut/equal? (sut/->color 0.2 0.5 0.5)
                      (sut/subtract c1 c2)))))

  (testing "multiplying colors"
    (let [c1 (sut/->color 1 0.2 0.4 )
          c2 (sut/->color 0.9 1 0.1)]
      (is (sut/equal? (sut/->color 0.9 0.2 0.04)
                      (sut/multiply c1 c2))))))
