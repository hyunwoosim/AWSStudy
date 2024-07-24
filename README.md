# 정리 노트

## 7.23
    1.chmod 600 과 chmod 700의 의미 (p.248)
        - chmod : change mode의 약자로 디렉토리의 권한을 변경하는 명령어이다.
        - 600 :  '6'은  소유자(user)의 권한을 나타낸다. '0'은  그룹과 기타에 대한 권한을 나타낸다.
        -   '6'은  읽기 쓰기 권한을 부여한다. '0'은 실행 권한이 없음을 의미한다.
        
        - 700 : '7' 은 소유자 권한을 나타낸다
        -   '7'은 읽기, 쓰기, 실행  권한을 부여한다.

## 7.24 
    ### 트러블 슈팅
    1. mysql을 다운받는중 이런 오류가 발생하였다.
        - MySQL 8.0 Community Server                                                                       3.0 MB/s | 3.1 kB     00:00
```
GPG key at file:///etc/pki/rpm-gpg/RPM-GPG-KEY-mysql-2022 (0x3A79BD29) is already installed
The GPG keys listed for the "MySQL 8.0 Community Server" repository are already installed but they are not correct for this package.
Check that the correct key URLs are configured for this repository.. Failing package is: mysql-community-client-8.0.39-1.el9.x86_64
GPG Keys are configured as: file:///etc/pki/rpm-gpg/RPM-GPG-KEY-mysql-2022
Public key for mysql-community-client-plugins-8.0.39-1.el9.x86_64.rpm is not installed. Failing package is: mysql-community-client-plugins-8.0.39-1.el9.x86_64
GPG Keys are configured as: file:///etc/pki/rpm-gpg/RPM-GPG-KEY-mysql-2022
Public key for mysql-community-common-8.0.39-1.el9.x86_64.rpm is not installed. Failing package is: mysql-community-common-8.0.39-1.el9.x86_64
GPG Keys are configured as: file:///etc/pki/rpm-gpg/RPM-GPG-KEY-mysql-2022
Public key for mysql-community-icu-data-files-8.0.39-1.el9.x86_64.rpm is not installed. Failing package is: mysql-community-icu-data-files-8.0.39-1.el9.x86_64
GPG Keys are configured as: file:///etc/pki/rpm-gpg/RPM-GPG-KEY-mysql-2022
Public key for mysql-community-libs-8.0.39-1.el9.x86_64.rpm is not installed. Failing package is: mysql-community-libs-8.0.39-1.el9.x86_64
GPG Keys are configured as: file:///etc/pki/rpm-gpg/RPM-GPG-KEY-mysql-2022
Public key for mysql-community-server-8.0.39-1.el9.x86_64.rpm is not installed. Failing package is: mysql-community-server-8.0.39-1.el9.x86_64
GPG Keys are configured as: file:///etc/pki/rpm-gpg/RPM-GPG-KEY-mysql-2022
The downloaded packages were saved in cache until the next successful transaction.
You can remove cached packages by executing 'dnf clean packages'.
Error: GPG check FAILED
```
- 구글링과 gpt를 사용하여 해결하였다.
  ```
    sudo rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2023
    ```
  - 이 명령어를 사용하여 GPG key를 최선 버젼으로 가져와야 해결이 되었다
  - 그 후
  ```
    sudo dnf clean all
    sudo dnf makecache
  ```
 - 패키지 메타데이터를 새로 고침하고 
 - 다시 설치하면 설치가 된다.

