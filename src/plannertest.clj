;Experimentation with the planner to figure out how it works

(require 'search.planner)

(def start-state
  '#{
     (con S1 S2)
     (con S2 S1)
     (at T1 S1)
     (at C1 S2)
     })

(def ops

  '{
    :unload{:name unload
            :achieves (at ?C ?S)
            :when  ((on ?C ?T) (at ?T ?S))
            :post ()
            :pre ()
            :del ( (on ?C ?T) )
            :add ( (at ?C ?S) )
            :cmd ( drop ?C )
            :txt (?T drops ?C at ?S)
            }
    :load
           {:name load
            :achieves (at ?C ?T)
            :when ((at ?T ?S) (at ?C ?S))
            :post ()
            :pre ()
            :del ( (at ?C ?S) )
            :add ( (on ?C ?t) )
            :cmd ( pickup ?C )
            :txt (?C picked up at ?S by ?T)
            }
    :move
           {:name move
            :achieves ((at ?T ?S2))
            :when ((con ?S1 ?S2) (con ?S2 ?S1))
            :post ()
            :pre ()
            :del ( (at ?T ?S1) )
            :add ( (at ?T ?S2) )
            :cmd ( move to ?S2 )
            :txt (?T moves to ?S2)
            }
    }
  )
;(planner start-state '(at C1 S1) ops)