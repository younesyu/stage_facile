import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-add-review',
  templateUrl: './add-review.component.html',
  styleUrls: ['./add-review.component.sass']
})
export class AddReviewComponent implements OnInit {
  internshipReviewFormGroup: FormGroup;
  editMode: boolean = false;

  /**
   * Valeurs par défaut
   */
  title = "Evaluez votre expérience selon les critères suivants"
  subject: string[];
  easeOfIntegration: string[];
  teamCommunication: string[];
  mentorship: string[];
  workload: string[];
  wouldRecommend: string[];


  constructor(private formBuilder: FormBuilder) {
    this.subject = [
      "1 - non conforme aux missions de la convention",
      "2 - assez différent des missions de la convention",
      "3 - légèrement différent des missions de la convention",
      "4 - correspondant parfaitement aux missions de la convention",
      "5 - correspondant parfaitement aux missions et au cursus"
    ];

    this.easeOfIntegration = [
      "1 - S'intégrer dans cet environnement est quasi-impossible",
      "2 - Environnement de travail difficile à intégrer",
      "3 - Environnement de travail relativement facile à intégrer",
      "4 - Environnement de travail facile à intégrer",
      "5 - Comme un poisson dans l'eau!"
    ];

    this.teamCommunication = [
      "1 - Communication inexistante avec les membres de l'équipe",
      "2 - Equipe distante",
      "3 - Communication présente avec les membres",
      "4 - Equipe accueillante",
      "5 - Communication présente et équipe chaleureuse"
    ];

    this.mentorship = [
      "1 - Tuteur quasi-jamais présent",
      "2 - Tuteur froid avec le stagiaire",
      "3 - Tuteur présent pour guider le stagiaire",
      "4 - Le tuteur encadre et est compréhensif avec le stagiaire",
      "5 - Tuteur pédagogue et mentor"
    ];

    this.workload = [
      "1 - Charge de travail complètement inadaptée",
      "2 - Charge de travail exigeante",
      "3 - Charge de travail peu équilibrée",
      "4 - Charge de travail équilibrée",
      "5 - Parfaite balance entre charge de travail et durée du stage"
    ];

    this.wouldRecommend = [
      "1 - Je ne recommande pas ce type de stage à cette entreprise",
      "2 - Je recommande peu ce type de stage à cette entreprise",
      "3 - Je suis neutre quant à la recommandation de ce type de stage",
      "4 - Je recommande ce type de stage à cette entreprise",
      "5 - Je recommande fortement ce type de stage à cette entreprise"
    ]
  }

  ngOnInit(): void {
    this.internshipReviewFormGroup = this.formBuilder.group({
      subject: '',
      easeOfIntegration: '',
      teamCommunication: '',
      mentorship: '',
      workload: '',
      wouldRecommend: '',
      content: ''
    });
  }

}
