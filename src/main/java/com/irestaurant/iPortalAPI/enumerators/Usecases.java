package com.irestaurant.iPortalAPI.enumerators;


public enum Usecases {
  None,
  Order_Send_To_Kitchen,
  Edit_Order,
  Delete_Order,
  Done_Order,
  Assign_Table_To_Order,//Table (Edited)
  Assign_Order_To_Table,//Order (Edited)
  Table_Add,
  Table_Update,
  Table_Remove,
  Table_Make_Available,
  Table_Send_To_Kitchen,
  Table_Modify_Send_To_Kitchen,
  Order_Modify_Send_To_Kitchen,
  Product_Add,
  Product_Update,
  Product_Remove,
  Category_Add,
  Category_Update,
  Category_Remove,

  User_Add,
  User_Update,

  Done_Order_Item,
  Undo_Order,
  Force_Stop_Order
}
