#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <sys/ioctl.h>
#include <linux/rtc.h>

#define MAX_COUNT			256
#define MAX_MALLOC_SIZE		(4*1024*1024)

int simple_memtest(void)
{
    int ret = 0;
    unsigned int *data[MAX_COUNT];
    unsigned int count = 0;
    unsigned int i, j;

    for (i = 0; i < MAX_COUNT; i++) {
        data[i] = (unsigned int *)malloc(MAX_MALLOC_SIZE);
        if (!data[i])
            break;
    }
    count = i;

    for (i = 0; i < count; i++) {
        volatile unsigned int *p = data[i];
        for (j = 0; j < MAX_MALLOC_SIZE/sizeof(unsigned int); j ++)
            p[j] = 0x5A5A1234 + j;
        printf("Memory test %3d: write %u bytes.\n", i, MAX_MALLOC_SIZE);
    }

    for (i = 0; i < count; i++) {
        volatile unsigned int *p = data[i];
        for (j = 0; j < MAX_MALLOC_SIZE/sizeof(unsigned int); j++) {
            if (p[j] != (0x5A5A1234 + j)) {
                ret = -1;
                printf("Memory test %3d: error occurs!\n", i);
                break;
            }
        }
        printf("Memory test %3d: read %u bytes.\n", i, MAX_MALLOC_SIZE);
    }

    for (i = 0; i < count; i++) {
        free(data[i]);
    }

    return ret;
}

static void rtc_dump_time(struct rtc_time *rtc)
{
    printf("rtc.tm_sec = %d\n", rtc->tm_sec);
    printf("rtc.tm_min = %d\n", rtc->tm_min);
    printf("rtc.tm_hour = %d\n", rtc->tm_hour);
    printf("rtc.tm_mday = %d\n", rtc->tm_mday);
    printf("rtc.tm_mon = %d\n", rtc->tm_mon);
    printf("rtc.tm_year = %d\n", rtc->tm_year);
    printf("rtc.tm_wday = %d\n", rtc->tm_wday);
    printf("rtc.tm_yday = %d\n", rtc->tm_yday);
    printf("rtc.tm_tm_isdst = %d\n", rtc->tm_isdst);
}

int rtc_set_time(char *timestr, int wday)
{
    int ret = -4;
    int fd;
    struct rtc_time rtc;

    printf("printf test");
    if (!timestr)
    {
        printf("the argument error");
        return -3;
    }

    memset(&rtc, 0, sizeof(struct rtc_time));
    sscanf(timestr, "%d-%d-%d %d:%d:%d",
           &rtc.tm_year, &rtc.tm_mon, &rtc.tm_mday,
           &rtc.tm_hour, &rtc.tm_min, &rtc.tm_sec);
    rtc.tm_year -= 1900;
    rtc.tm_mon -= 1;
    rtc.tm_wday = wday;
    rtc_dump_time(&rtc);

    fd = open("/dev/rtc0", O_RDWR);
    if (fd < 0 ) {
        printf("Cannot open /dev/rtc0\n");
        return -2;
    }

    ret = ioctl(fd, RTC_SET_TIME, &rtc);
    if (ret < 0)
        printf("RTC_SET_TIME failed: %d\n", errno);

    close(fd);
    return ret;
}

int rtc_get_time(char *timestr)
{
    int ret = -1;
    int fd;
    struct rtc_time rtc;

    if (!timestr)
        return -1;

    fd = open("/dev/rtc0", O_RDWR);
    if (fd < 0 ) {
        printf("Cannot open /dev/rtc0\n");
        return -2;
    }

    ret = ioctl(fd, RTC_RD_TIME, &rtc);
    if (ret < 0) {
        printf("RTC_RD_TIME failed: %d\n", errno);
    } else {
        rtc_dump_time(&rtc);
        sprintf(timestr, "%d-%02d-%02d %02d:%02d:%02d",
                rtc.tm_year+1900, rtc.tm_mon+1, rtc.tm_mday,
                rtc.tm_hour, rtc.tm_min, rtc.tm_sec);
    }
    close(fd);
    return ret;
}

/*
int main(void)
{
	int ret;
	char buf[BUFSIZ];
	int wday = 0;
	ret = rtc_get_time(buf);
	if (ret == 0) {
		printf("%s\n", buf);
		rtc_set_time(buf, wday);
	}
	return 0;
}
*/
