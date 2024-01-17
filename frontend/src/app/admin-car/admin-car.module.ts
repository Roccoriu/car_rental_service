import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputNumberModule } from 'primeng/inputnumber';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { DialogModule } from 'primeng/dialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TagModule } from 'primeng/tag';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { FileUploadModule } from 'primeng/fileupload';
import { InputTextModule } from 'primeng/inputtext';
import { InputSwitchModule } from 'primeng/inputswitch';
import { DropdownModule } from 'primeng/dropdown';
import { MultiSelectModule } from 'primeng/multiselect';
import { AdminCarDialogComponent } from './components/admin-car-dialog/admin-car-dialog.component';
import { AdminCarListComponent } from './views/admin-car-list/admin-car-list.component';
import { DialogService } from 'primeng/dynamicdialog';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { CalendarModule } from 'primeng/calendar';




@NgModule({
  declarations: [AdminCarDialogComponent, AdminCarListComponent],
  imports: [
    CommonModule,
    TableModule,
    ButtonModule,
    InputNumberModule,
    ToastModule,
    ToolbarModule,
    DialogModule,
    ConfirmDialogModule,
    FormsModule,
    TagModule,
    ReactiveFormsModule,
    InputTextareaModule,
    FileUploadModule,
    InputTextModule,
    InputSwitchModule,
    DropdownModule,
    MultiSelectModule,
    ConfirmPopupModule,
    CalendarModule
  ],
  providers: [DialogService, ConfirmationService, MessageService]
})
export class AdminCarModule { }
