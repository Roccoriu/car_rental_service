import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CarRentalComponent } from './car/views/car-rental/car-rental.component';

const routes: Routes = [
  {path: '', component: CarRentalComponent},
  {path: 'home', component: CarRentalComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
