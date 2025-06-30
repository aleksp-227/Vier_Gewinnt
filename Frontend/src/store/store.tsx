import {createContext, ReactNode, useMemo, useState} from 'react';
import { Game } from './props';


interface Actions {
  setGame(value: Game): void,
}

export interface ConfigState {
  game: Game,
  actions: Actions,
}

const initialGame: Game = {
  board: [],
  id: "1",
  message: "Game not initialized",
  currentPlayer: {
    name: "Rot",
    token: "X",
  }
}

const initialState: ConfigState = {
  game: initialGame,
  actions: {
    setGame: function (): void {
      throw new Error('Not initialized');
    },
  },
};

export const StoreContext = createContext<ConfigState>(initialState);

function useStore() {
  const [game, setGame] = useState<Game>(initialGame);

  return useMemo(() => ({
    game,
    actions: {
      setGame: setGame,
    }
  }), [game]);
}

export default function StoreProvider({children}: { children: ReactNode }) {
  return (
    <StoreContext.Provider value={useStore()}>
      {children}
    </StoreContext.Provider>
  );
}