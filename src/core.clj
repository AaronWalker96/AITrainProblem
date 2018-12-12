;All search algorithms are in the "search" folder
;They can be imported like so
(require 'search.opsearch)
(require 'search.planner)
(require 'ourastarsearch)
(require 'plannertest)

;(A*search {:state 'a, :cost 0} (fn [x] (= x 'h)) a*lmg-map)
;(stateAdapter (:cmd (planner ss-one '(cas cargo-one station-two ) ops)))


(defn trainthing []
  (let[
       outputcmd (stateAdapter (apply str (:cmd(planner ss-one '(cas cargo-one station-two ) ops))))
       ]

    (loop[x 0, in outputcmd]
    (

      (def start (nth (clojure.string/split (str in) #",") 0))
      ;;(println start)
      ;;get x then split
      ;;
      ;;a start

      ))

    )




  ;(def move (nth (clojure.string/split formatted #"move") (- x 1)))

  ;state planner
  ;goal planner
  ;a * map

  )
