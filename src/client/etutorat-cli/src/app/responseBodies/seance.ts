import { Tuteur } from './tuteur';
import { Salle } from './Salle';
import { Tutore } from './tutore';

export class Seance {
    id: number;
    dateDebut: Date;
    dateFin: Date;
    outilAV: string;
    sujet: string;
    nbmaxtutores: number;
    tuteur: Tuteur;
    salle: Salle;
    tutores: Tutore[];
  
    
}