;All provided search algorithms are in the "search" folder
;With ourastarsearch being a modified a* search
;They can be imported like so
(require 'search.opsearch)
(require 'search.planner)
(require 'ourastarsearch)
(require 'plannertest)

;(plan-route ss-one '(cas cargo-one station-two) map1)

;;Plan-route is used with single goals
;;Plan-route is used with multiple goals

(defn plan-route [planner-ss goal a*map ]
  (let [
        plannerout (plan planner-ss goal ops)
        outputcmd (stateAdapter (apply str (:cmd plannerout)))
        ]

    (println (:txt plannerout))

    (loop [current outputcmd results [] po (:txt plannerout)]

      (if (empty? current)
        (println  "Planner Results are: " po " A* Results are: " results)
        (recur (rest current) (conj results (run-astar a*map (first current))) po))
      )
    )
  )

(defn plan-route-multiple-goals [planner-ss goal a*map ]
  (let [
        plannerout (plan-multiple planner-ss goal ops)
        outputcmd (stateAdapter (apply str (:cmd plannerout)))
        ]

    (println (:txt plannerout))

    (loop [current outputcmd results [] po (:txt plannerout)]

      (if (empty? current)
        (println  "Planner Results are: " po " A* Results are: " results)
        (recur (rest current) (conj results (run-astar a*map (first current))) po))
      )
    )
  )

(defn run-astar [a-map goals]
  (let
    [
     stations (clojure.string/split goals #",")
     ]
    (println (first stations))
    (println (first (rest stations)))
    (A*search {:state (symbol (first stations)), :cost 0} (fn [x] (= x (symbol (first (rest stations))))) a-map)
    )
  )

(defn planner-test-all-ss []
  (println "Start-State-one"(:cmd (plan ss-one '(cas cargo-one station-two ) ops)))
  (println "Start-State-two" (:cmd (plan ss-two '(cas cargo-one station-two ) ops)))
  (println "Start-State-three" (:cmd (plan ss-three '(cas cargo-one station-four ) ops)))
  (println "Start-State-four" (:cmd (plan ss-three '(cas cargo-one station-four ) ops)))
  (println "Start-State-five" (:cmd (plan-multiple ss-five '((cas cargo-one station-four)(cas cargo-two station-two)) ops)))
  (println "Start-State-six" (:cmd (plan-multiple ss-six '((cas cargo-one station-two)(cas cargo-two station-five)) ops)))
  (println "Start-State-seven" (:cmd (plan ss-seven '(cas cargo-one station-one) ops)))
  (println "Start-State-eight" (:cmd (plan ss-eight '(cas cargo-one station-four) ops)))
  (println "Start-State-nine" (:cmd (plan ss-nine '(cas cargo-one station-eleven) ops)))
  )


(defn planner-test-small-ss []
  (println "Start-State-one"(:cmd (plan ss-one '(cas cargo-one station-two ) ops)))
  (println "Start-State-two" (:cmd (plan ss-two '(cas cargo-one station-two ) ops)))
  (println "Start-State-three" (:cmd (plan ss-three '(cas cargo-one station-four ) ops)))
  (println "Start-State-four" (:cmd (plan ss-three '(cas cargo-one station-four ) ops)))
  (println "Start-State-five" (:cmd (plan-multiple ss-five '((cas cargo-one station-four)(cas cargo-two station-two)) ops)))
  (println "Start-State-six" (:cmd (plan-multiple ss-six '((cas cargo-one station-two)(cas cargo-two station-five)) ops)))
  (println "Start-State-seven" (:cmd (plan ss-seven '(cas cargo-one station-one) ops)))
  (println "Start-State-eight" (:cmd (plan ss-eight '(cas cargo-one station-four) ops)))
  (println "Start-State-nine" (:cmd (plan ss-nine '(cas cargo-one station-eleven) ops)))
  )

;(defn planner-test-medium-ss []
  ;(println "Start-State-ten"(:cmd (plan ss-ten '(cas cargo-one station-two ) ops)))
  ;(println "Start-State-eleven" (:cmd (plan ss-eleven '(cas cargo-one station-two ) ops)))
 ; )

(defn planner-test-large-ss []
  (println "Start-State-twelve"(:cmd (plan-multiple ss-twelve '((cas llama station-newcastle)(cas scottish-notes station-edinburgh)(cas neds station-brighton)(cas sheep station-edinburgh)(cas brenda station-bristol)(cas cheese station-york) ) ops)))
  )

(defn planner-scale-test-all-ss []
  (println "Scaletest-Start-State-One"(:cmd (plan ss-scaletest '(cas cargo-one station-five) ops)))
  (println "Scaletest-Start-State-two"(:cmd (plan-multiple ss-scaletest '((cas cargo-one station-five)(cas cargo-two station-six)) ops)))
  (println "Scaletest-Start-State-three"(:cmd (plan-multiple ss-scaletest '((cas cargo-one station-five)(cas cargo-two station-six)(cas cargo-three station-seven)) ops)))
  (println "Scaletest-Start-State-four"(:cmd (plan-multiple ss-scaletest '((cas cargo-one station-five)(cas cargo-two station-six)(cas cargo-three station-seven)(cas cargo-four station-eight)) ops)))
  (println "Scaletest-Start-State-five"(:cmd (plan-multiple ss-scaletest '((cas cargo-one station-five)(cas cargo-two station-six)(cas cargo-three station-seven)(cas cargo-four station-eight)(cas cargo-five station-nine)) ops)))
  (println "Scaletest-Start-State-six"(:cmd (plan-multiple ss-scaletest '((cas cargo-one station-five)(cas cargo-two station-six)(cas cargo-three station-seven)(cas cargo-four station-eight)(cas cargo-five station-nine)(cas cargo-six station-ten)) ops)))
  (println "Scaletest-Start-State-seven"(:cmd (plan-multiple ss-scaletest '((cas cargo-one station-five)(cas cargo-two station-six)(cas cargo-three station-seven)(cas cargo-four station-eight)(cas cargo-five station-nine)(cas cargo-six station-ten)(cas cargo-seven station-eleven)) ops)))
  (println "Scaletest-Start-State-eight"(:cmd (plan-multiple ss-scaletest '((cas cargo-one station-five)(cas cargo-two station-six)(cas cargo-three station-seven)(cas cargo-four station-eight)(cas cargo-five station-nine)(cas cargo-six station-ten)(cas cargo-seven station-eleven)(cas cargo-eight station-twelve)) ops)))
  (println "Scaletest-Start-State-nine"(:cmd (plan-multiple ss-scaletest '((cas cargo-one station-five)(cas cargo-two station-six)(cas cargo-three station-seven)(cas cargo-four station-eight)(cas cargo-five station-nine)(cas cargo-six station-ten)(cas cargo-seven station-eleven)(cas cargo-eight station-twelve)(cas cargo-nine station-one)) ops)))
  (println "Scaletest-Start-State-ten"(:cmd (plan-multiple ss-scaletest '((cas cargo-one station-five)(cas cargo-two station-six)(cas cargo-three station-seven)(cas cargo-four station-eight)(cas cargo-five station-nine)(cas cargo-six station-ten)(cas cargo-seven station-eleven)(cas cargo-eight station-twelve)(cas cargo-nine station-one)(cas cargo-ten station-two)) ops)))
  (println "Scaletest-Start-State-eleven"(:cmd (plan-multiple ss-scaletest '((cas cargo-one station-five)(cas cargo-two station-six)(cas cargo-three station-seven)(cas cargo-four station-eight)(cas cargo-five station-nine)(cas cargo-six station-ten)(cas cargo-seven station-eleven)(cas cargo-eight station-twelve)(cas cargo-nine station-one)(cas cargo-ten station-two)(cas cargo-eleven station-three)) ops)))
  (println "Scaletest-Start-State-twelve"(:cmd (plan-multiple ss-scaletest '((cas cargo-one station-five)(cas cargo-two station-six)(cas cargo-three station-seven)(cas cargo-four station-eight)(cas cargo-five station-nine)(cas cargo-six station-ten)(cas cargo-seven station-eleven)(cas cargo-eight station-twelve)(cas cargo-nine station-one)(cas cargo-ten station-two)(cas cargo-eleven station-three)(cas cargo-twelve station-four)) ops)))
  )