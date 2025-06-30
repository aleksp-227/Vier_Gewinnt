export type Game = {
    board: any[],
    id: string,
    message: string,
    currentPlayer: Player
}
export type Player = {
    name: string,
    token: string
}