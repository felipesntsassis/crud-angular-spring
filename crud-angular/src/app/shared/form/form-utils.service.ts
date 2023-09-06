import { Injectable } from '@angular/core';
import { FormControl, UntypedFormArray, UntypedFormControl, UntypedFormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FormUtilsService {

  getErrorMessage(formGroup: UntypedFormGroup, fieldName: string) {
    const field = <UntypedFormControl> formGroup.get(fieldName);
    return this.getErrorMessageFromField(field)
  }

  getErrorMessageFromField(field: UntypedFormControl) {
    if (field?.hasError('required')) {
      return 'Campo obrigatório';
    }
    if(field?.hasError('minlength')) {
      const requiredLength = field.errors ? field.errors['minlength']['requiredLength'] : 5;

      return `Tamanho mínimo precisa ser de ${requiredLength} caracteres.`;
    }
    if(field?.hasError('maxlength')) {
      const requiredLength = field.errors ? field.errors['maxlength']['requiredLength'] : 100;

      return `Tamanho máximo excedido de ${requiredLength} caracteres.`;
    }

    return 'Campo inválido';
  }

  getFormArrayFieldErrorMessage(formGroup: UntypedFormGroup, formArrayName: string, fieldName: string, index: number) {
    const formArray = <UntypedFormArray> formGroup.get(formArrayName);
    const field = <UntypedFormControl> formArray.controls[index].get(fieldName);

    return this.getErrorMessageFromField(field);
  }

  isFormArrayRequired(form: UntypedFormGroup, formArrayName: string) {
    const formArray = <UntypedFormArray> form.get(formArrayName);

    return !formArray.valid && formArray.hasError('required') && formArray.touched;
  }

  validateAllFormFields(formGroup: UntypedFormGroup | UntypedFormArray) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);

      if (control instanceof UntypedFormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof UntypedFormGroup || control instanceof UntypedFormArray) {
        control.markAsTouched({ onlySelf: true });
        this.validateAllFormFields(control);
      }
    });
  }

}
