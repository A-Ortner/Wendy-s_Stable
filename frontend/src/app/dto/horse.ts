export class Horse {
  id: number;
  name: string;
  sex: string;
  dateOfBirth: Date;
  description: string;
  favSport: number;

  setSex(s: string){
    this.sex = s;
}
}

