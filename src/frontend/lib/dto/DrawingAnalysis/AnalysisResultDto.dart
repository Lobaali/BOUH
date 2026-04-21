class AnalysisResult {
  final String drawingId;
  final String emotion;

  /// Empty string if Gemini failed — never null.
  final String emotionalInterpretation;

  /// Empty list when threshold not met — never null.
  final List<String> doctorIds;

  const AnalysisResult({
    required this.drawingId,
    required this.emotion,
    required this.emotionalInterpretation,
    required this.doctorIds,
  });

  factory AnalysisResult.fromJson(Map<String, dynamic> json) {
    return AnalysisResult(
      drawingId: (json['drawingId'] ?? '').toString(),
      emotion: (json['emotion'] ?? '').toString(),
      emotionalInterpretation: (json['emotionalInterpretation'] ?? '')
          .toString(),
      doctorIds: (json['doctorIds'] as List? ?? [])
          .map((e) => e.toString())
          .toList(),
    );
  }
}
