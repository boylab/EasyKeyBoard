# EasyKeyBoard
 
 ### 仅对横向平板适配，竖屏或手机请修改dimens.xml即可
```
values/dimens.xml       //竖屏
values-land/dimens.xml  //横屏

也可扩展其他value下的尺寸
```

 ### AndroidManifest.xml中禁止添加这行代码
```
android:hardwareAccelerated="false"   //禁止使用
```