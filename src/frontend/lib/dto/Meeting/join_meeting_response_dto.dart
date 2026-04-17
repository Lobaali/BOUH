class JoinMeetingResponseDto {
  final String appId;
  final String channelName;
  final String token;
  final int uid;
  final String appointmentId;
  final String role;

  JoinMeetingResponseDto({
    required this.appId,
    required this.channelName,
    required this.token,
    required this.uid,
    required this.appointmentId,
    required this.role,
  });

  factory JoinMeetingResponseDto.fromJson(Map<String, dynamic> json) {
    return JoinMeetingResponseDto(
      appId: json['appId'] as String? ?? '',
      channelName: json['channelName'] as String? ?? '',
      token: json['token'] as String? ?? '',
      uid: (json['uid'] as num?)?.toInt() ?? 0,
      appointmentId: json['appointmentId'] as String? ?? '',
      role: json['role'] as String? ?? 'publisher',
    );
  }
}
