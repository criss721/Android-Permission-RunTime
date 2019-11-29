# Android-Permission-RunTime
copy/paste this class in your project [MyPermissionHandler.java](https://github.com/criss721/Android-Permission-RunTime/blob/master/MyPermissionHandler.java)

# How To Use : 

```java
  findViewById(R.id.imgAvatarActivityProfileInfo).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        
        new MyPermissionHandler().request(ActivityProfileInfo.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, new MyPermissionHandler.RequestListener() {
          @Override
          public void onGranted(String permission) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, galleryResultCode);
          }

          @Override
          public void onDenied(String permission) {
            G.toast("ACCESS EXTERNAL STORAGE REQUIRED");
          }

          @Override
          public void onTaskDone() {}
        });
      }
    });
    
      @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    MyPermissionHandler.onRequestPermissionResult(requestCode, permissions, grantResults);
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }
```
