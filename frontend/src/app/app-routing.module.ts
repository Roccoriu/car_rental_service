import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CarRentalComponent } from './car/views/car-rental/car-rental.component';
import { AdminCarListComponent } from './admin-car/views/admin-car-list/admin-car-list.component';

const routes: Routes = [
  {path: '', component: CarRentalComponent},
  {path: 'home', component: CarRentalComponent},
  {path: 'admin', component: AdminCarListComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
