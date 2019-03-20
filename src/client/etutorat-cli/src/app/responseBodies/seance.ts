import { Tuteur } from './tuteur';
import { Salle } from './Salle';

export class Seance {
    id: number;
    dateDebut: Date;
    dateFin: Date;
    outilAV: string;
    sujet: string;
    nbmaxtutores: number;
    tuteur: Tuteur;
    salle: Salle;

  
    
}