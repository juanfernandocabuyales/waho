import { Directive, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { FileDto } from '../models/general/general';

@Directive({
  selector: '[appNgDropFiles]'
})
export class NgDropFilesDirective {

  @Input()
  archivo: FileDto[] = [];

  @Output()
  mouseSobre: EventEmitter<boolean> = new EventEmitter();

  constructor() { }

  @HostListener('dragover', ['$event'])
  public onDragEnter(event: any): void {
    this.mouseSobre.emit(true);
    this.prevenirDetener(event);
  }

  @HostListener('dragleave', ['$event'])
  public onDragLeave(event: any): void {
    this.mouseSobre.emit(false);
  }

  @HostListener('drop', ['$event'])
  public onDrop(event: any): void {

    const transferencia = this.getTransferencia(event);

    if (!transferencia) {
      return;
    }

    this.extraerArchivos(transferencia.files);

    this.prevenirDetener(event);

    this.mouseSobre.emit(false);
  }

  private extraerArchivos(archivosLista: FileList): void {
    const fileAux: File = archivosLista[0];

    if (this.esImagen(fileAux.type) && !this.validarNombre(fileAux.name)) {
      this.archivo.push({
        file: fileAux,
        nombre: fileAux.name
      });
    }
    console.log('this.archivo ->', this.archivo);
  }

  private getTransferencia(event: any): any {
    return event.dataTransfer ? event.dataTransfer : event.originalEvent.dataTransfer;
  }

  private prevenirDetener(event): void {
    event.preventDefault();
    event.stopPropagation();
  }

  private validarNombre(pNombre: string): boolean {
    const fileAux = this.archivo.find(item => item.file.name === pNombre);
    return fileAux ? true : false;
  }

  private esImagen(tipoArchivo: string): boolean {
    return (tipoArchivo === '' || tipoArchivo === undefined) ? false : tipoArchivo.startsWith('image');
  }

}
