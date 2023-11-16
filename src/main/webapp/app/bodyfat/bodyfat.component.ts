import { Component, OnInit } from '@angular/core';
import { KoerperfettService } from 'app/entities/koerperfett/service/koerperfett.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import './bodyfat.component.scss';
import dayjs from 'dayjs/esm';

@Component({
  selector: 'jhi-bodyfat',
  templateUrl: './bodyfat.component.html',
  styleUrls: ['./bodyfat.component.scss'],
})
export class BodyfatComponent implements OnInit {
  bodyfatForm: FormGroup;
  geschlecht: string = 'männlich';
  koerperfettanteil: number = 0; // Initialize to 0 or another appropriate default value
  success: boolean | undefined;
  url: string | undefined;
  showMeasurementsPage: boolean = false; // Initialize as false

  constructor(private koerperfettService: KoerperfettService, private formBuilder: FormBuilder) {
    this.bodyfatForm = this.formBuilder.group({
      privatoderfirma: [true],
      geschlecht: ['männlich', Validators.required],
      bauchumfang: ['', Validators.required],
      nackenumfang: ['', Validators.required],
      koerpergroesse: ['', Validators.required],
      hueftumfang: [''],
      age: ['', Validators.required],
      success: [''],
      koerperfettanteil: [''],
      datumundZeit: [dayjs()],
    });
  }

  ngOnInit(): void {}

  saveKoerperfettData(data: any) {
    this.koerperfettService.create(data).subscribe(
      (response: any) => {
        this.success = true;
        this.url = response.url;
        if (response.body && response.body.koerperfettanteil) {
          this.koerperfettanteil = response.body.koerperfettanteil;
        }
      },
      (error: any) => {
        this.success = false;
        console.error('Error saving Koerperfett data:', error);
      }
    );
  }

  calculateKoerperfettanteil() {
    const formValues = this.bodyfatForm.value;

    this.bodyfatForm.patchValue({
      koerperfettanteil: this.koerperfettanteil,
    });

    this.saveKoerperfettData(this.bodyfatForm.value);
  }

  proceedToMeasurements() {
    const privatoderfirma = this.bodyfatForm.get('privatoderfirma')?.value;

    if (privatoderfirma === undefined) {
      alert('Bitte füllen Sie alle Felder aus.');
      return;
    }

    this.showMeasurementsPage = true;
  }

  getResultMessage(): string {
    if (this.geschlecht === 'männlich') {
      if (this.koerperfettanteil <= 20) {
        return 'Gut (Unter 20%)';
      } else if (this.koerperfettanteil <= 25) {
        return 'Medium (20% - 25%)';
      } else {
        return 'Hoch (Über 25%)';
      }
    } else {
      if (this.koerperfettanteil <= 25) {
        return 'Gut (Unter 25%)';
      } else if (this.koerperfettanteil <= 30) {
        return 'Medium (25% - 30%)';
      } else {
        return 'Hoch (Über 30%)';
      }
    }
  }
  getColorClass(): string {
    if (this.geschlecht === 'männlich') {
      if (this.koerperfettanteil <= 20) {
        return 'gut';
      } else if (this.koerperfettanteil <= 25) {
        return 'medium';
      } else {
        return 'hoch';
      }
    } else {
      if (this.koerperfettanteil <= 25) {
        return 'gut';
      } else if (this.koerperfettanteil <= 30) {
        return 'medium';
      } else {
        return 'hoch';
      }
    }
  }
  openResult() {}
}
