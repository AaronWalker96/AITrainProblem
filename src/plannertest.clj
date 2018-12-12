;Experimentation with the planner to figure out how it works

(require 'search.planner)


(def ss3b
  '#{(station london)
     (station ncl)
     (station edin)
     (station duram)
     (station sunder)
     (isa ncl-exp train)
     (tas ncl-exp london)
     (cas llama ncl)
     })

(def ops
  '{
    :unload
    {:name unload
     :achieves (cas ?stuff ?stn)
     :when ((isa ?t train))
     :post ((cot ?stuff ?t)(tas ?t ?stn))
     :pre ()
     :del ( (cot ?stuff ?t))
     :add ((cas ?stuff ?stn))
     :cmd ((drop ?stuff))
     :txt ((?t drops ?stuff at ?stn) )
     }
    :load
    {:name load
     :achieves (cot ?stuff ?t)
     :when ((cas ?stuff ?stn))
     :post ((tas ?t ?stn))
     :pre ()
     :del ((cas ?stuff ?stn) )
     :add ((cot ?stuff ?t) )
     :cmd ((pickup ?stuff))
     :txt ((?stuff picked up at ?stn by ?t))
     }
    :move
    {:name move
     :achieves (tas ?t ?dst)
     :when ((tas ?t ?src))
     :post ()
     :pre ()
     :del ((tas ?t ?src))
     :add ((tas ?t ?dst))
     :cmd ((move ?t ?dst ?src))
     :txt ((?t moves to ?dst from ?src))
     }
    }
  )

(def ss-one
  '#{(station station-one)
     (station station-two)
     (station station-three)
     (isa train-one train)
     (tas train-one station-one)
     (cas cargo-one station-three)
     })

(def ss-two
  '#{(station station-one)
     (station station-two)
     (station station-three)
     (isa train-one train)
     (tas train-one station-one)
     (cas cargo-one station-one)
     })

(def ss-three
  '#{(station station-one)
     (station station-two)
     (station station-three)
     (station station-four)
     (station station-five)
     (isa train-one train)
     (tas train-one station-one)
     (cas cargo-one station-two)
     })

(def ss-four
  '#{(station station-one)
     (station station-two)
     (station station-three)
     (station station-four)
     (isa train-one train)
     (tas train-one station-one)
     (cas cargo-one station-three)
     (cas cargo-two station-four)
     })

(def ss-five
  '#{(station station-one)
     (station station-two)
     (station station-three)
     (station station-four)
     (station station-five)
     (isa train-one train)
     (isa train-two train)
     (tas train-one station-one)
     (tas train-two station-five)
     (cas cargo-one station-two)
     (cas cargo-two station-four)
     })

(def ss-six
  '#{(station station-one)
     (station station-two)
     (station station-three)
     (station station-four)
     (station station-five)
     (isa train-one train)
     (tas train-one station-one)
     (cas cargo-one station-three)
     (cas cargo-two station-four)
     })

(def ss-seven
  '#{(station station-one)
     (station station-two)
     (station station-three)
     (station station-four)
     (isa train-one train)
     (tas train-one station-three)
     (cas cargo-one station-one)
     })

(def ss-eight
  '#{(station station-one)
     (station station-two)
     (station station-three)
     (station station-four)
     (station station-five)
     (cas cargo-one station-three)
     })

(def ss-nine
  '#{(station station-one)
     (station station-two)
     (station station-three)
     (station station-four)
     (station station-five)
     (station station-six)
     (station station-seven)
     (station station-eight)
     (station station-nine)
     (station station-ten)
     (station station-eleven)
     (station station-twelve)
     (isa train-one train)
     (tas train-one station-one)
     (cas cargo-one station-twelve)
     })


;(planner ss-one '(cas cargo-one station-two ) ops)
;(planner ss-two '(cas cargo-one station-two ) ops)
;(planner ss-three '(cas cargo-one station-four ) ops)
;(planner (:state (planner ss-four '(cas cargo-one station-two) ops)) '(cas cargo-two station-two) ops)
;(planner (:state (planner ss-five '(cas cargo-one station-four) ops)) '(cas cargo-two station-two) ops)
;(planner (:state (planner ss-six '(cas cargo-one station-two) ops)) '(cas cargo-two station-five) ops)
;(planner ss-seven '(cas cargo-one station-one) ops)
;(planner ss-eight '(cas cargo-one station-four) ops)
;(planner ss-nine '(cas cargo-one station-eleven) ops)

;(planner ss-four '((cas cargo-one station-two)(cas cargo-two station-two)) ops)