(ns rays.matrix-test
  (:require [rays.matrix :as sut]
            [rays.common :as cm]
            [clojure.test :refer [deftest testing is]]))

(deftest test-matrix
  (testing "constructing and inspecting a 4x4 matrix"
    (let [m     (sut/->mat4 1    2    3    4
                            5.5  6.5  7.5  8.5
                            9    10   11   12
                            13.5 14.5 15.5 16.5)
          cases [[0 0 1]
                 [1 0 5.5]
                 [1 2 7.5]
                 [2 2 11]
                 [3 0 13.5]
                 [3 2 15.5]]]

      (doseq [[r c expected :as case] cases]
        (is (cm/eq-floats? expected (sut/at m r c))
            (str case))))))
