(ns rays.colors-test
  (:require [rays.colors :as sut]
            [clojure.test :refer [testing deftest is]]
            [rays.common :as c]))

(deftest colors-test
  (testing "constructing a color"
    (let [c (sut/->color 0.1 0.2 0.3)]
      (is (c/eq-floats? 0.1 (:red c)))
      (is (c/eq-floats? 0.2 (:green c)))
      (is (c/eq-floats? 0.3 (:blue c))))))
