
export class Admin {
    id: number;
    nom: string;
    prenom: string;
    email: string;

    constructor(id, nom, prenom, email){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }
    
}