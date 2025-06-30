import axios from 'axios';

const BASE_URL = 'http://localhost:8080';

export async function newGame() {
    return await axios.get(`${BASE_URL}/game/initGame`);
}

export async function move(col: number, id: string) {
    const data = {column: col, id: id};
    const response = await axios.post(`${BASE_URL}/game/makeMove`, data);
    console.log(response.data);
    return response.data;
}

